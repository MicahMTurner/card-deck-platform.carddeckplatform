package carddeckplatform.game;

import java.util.ArrayList;
import java.util.Set;

import org.newdawn.slick.geom.Circle;

import communication.link.HostFinder;
//import communication.link.HostId;
import communication.link.TcpHostFinder;
import communication.link.TcpIdListener;

import carddeckplatform.game.gameEnvironment.GameEnvironment;
import carddeckplatform.game.gameEnvironment.GameEnvironment.ConnectionType;
import client.dataBase.ClientDataBase;
import client.dataBase.DynamicLoader;

import logic.host.Host;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ViewFlipper;

public class CarddeckplatformActivity extends Activity {
	private static Context context;
	private ViewFlipper mFlipper;
	private boolean livePosition;

	// constatnts for request code
	final private int MARKET = 50;
	final private int PLUGINRANK = 51;
	final private int FINDHOSTS = 51;
	AvailableHosts availableHosts;
	public static Context getContext() {
		return context;
	}

	private void getPrefs() {
		// Get the xml/preferences.xml preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		boolean useBluetooth = prefs.getBoolean("useBluetooth", false);
		boolean useLivePosition = prefs.getBoolean("useLivePosition", false);
		if (!useBluetooth) {
			GameEnvironment.get().setConnectionType(ConnectionType.TCP);
		} else {
			GameEnvironment.get().setConnectionType(ConnectionType.BLUETOOTH);
		}
		if (useLivePosition) {
			livePosition = true;
		}

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		// tcpIdListener=null;
		// host=null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		Display display = getWindowManager().getDefaultDisplay();
		GameEnvironment.get().getDeviceInfo()
				.setScreenWidth(display.getWidth());
		GameEnvironment.get().getDeviceInfo()
				.setScreenHeight(display.getHeight());
		GameEnvironment.get().getDeviceInfo()
				.setRotationAngle(display.getRotation());

		context = CarddeckplatformActivity.this;

		// making some wifi
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();

		String ipStr = String.format("%d.%d.%d.%d", (ipAddress & 0xff),
				(ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
				(ipAddress >> 24 & 0xff));

		// GameStatus.localIp = ipStr;
		GameEnvironment.get().getTcpInfo().setLocalIp(ipStr);
		// *********************************************
		// making widgets
		// *********************************************

		// making the flipper
		mFlipper = ((ViewFlipper) this.findViewById(R.id.welcomeFlipper));
		mFlipper.startFlipping();
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_up_in));
		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_up_out));

		final EditText username = (EditText) findViewById(R.id.nickText);

		// take user name from prefs
		username.setText(PreferenceManager.getDefaultSharedPreferences(
				getBaseContext()).getString("userName", "Guest"));

		// bring curser to end of text
		username.setSelection(username.length());

		Button hostBtn = (Button) findViewById(R.id.creategameButton);
		Button joinBtn = (Button) findViewById(R.id.joingamebutton);
		hostBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				final Dialog dialog = new Dialog(CarddeckplatformActivity.this);
				GameEnvironment.get().getPlayerInfo().setServer(true);
				GameEnvironment.get().getTcpInfo().setHostIp("127.0.0.1");
				GameEnvironment.get().getPlayerInfo()
						.setUsername(username.getText().toString());

				dialog.setContentView(R.layout.gamelistdialog);
				// gets user prefs.
				getPrefs();
				dialog.setTitle("Please choose a game");
				Set<String> games = ClientDataBase.getDataBase()
						.getGamesNames();

				LinearLayout ll = (LinearLayout) dialog
						.findViewById(R.id.gameListLayout);
				for (final String gameName : games) {
					Button gameBtn = new Button(getApplicationContext());
					gameBtn.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.graybutton));
					gameBtn.setText(gameName);
					gameBtn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							Intent i;
							if (gameName.equals("free play"))
								i = new Intent(CarddeckplatformActivity.this,
										ProfileCatalogActivity.class);
							else
								i = new Intent(CarddeckplatformActivity.this,
										GameActivity.class);
							// always use the tcp server socket since we always
							// need it to connect the hosting player.
							GameEnvironment.get().getTcpInfo()
									.initServerSocket();

							i.putExtra("gameName", gameName);
							i.putExtra("livePosition", livePosition);
							startActivity(i);

							dialog.dismiss();
						}
					});
					ll.addView(gameBtn);
				}
				dialog.show();
			}
		});

		joinBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

				GameEnvironment.get().getPlayerInfo().setServer(false);

				GameEnvironment.get().getPlayerInfo()
						.setUsername(username.getText().toString());

				// making dialog to get ip
				final Dialog dialog = new Dialog(CarddeckplatformActivity.this);
				availableHosts = new AvailableHosts(
						CarddeckplatformActivity.this, dialog);
				// set table size
				LayoutParams params = new LayoutParams(700, 500);
				dialog.setContentView(availableHosts.getTable(), params);

				LinearLayout ll = (LinearLayout) dialog
						.findViewById(R.id.gameListLayout);
				// gets user prefs.
				getPrefs();

				if (GameEnvironment.get().getConnectionType() == ConnectionType.TCP) {
					if (!haveInternet(getContext()))
						showNoConnectionDialog(getContext(), FINDHOSTS);
					else{
						HostFinder hostFinder = new HostFinder();
						hostFinder.addObserver(availableHosts);
						hostFinder.findHosts();
						
					}

				} else if (GameEnvironment.get().getConnectionType() == ConnectionType.BLUETOOTH) {
					BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
							.getDefaultAdapter();
					if (mBluetoothAdapter == null) {
						// Device does not support Bluetooth
						System.out.println("Device does not support Bluetooth");
					}

					Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
							.getBondedDevices();

					if (pairedDevices.size() > 0) {
						// Loop through paired devices
						for (final BluetoothDevice device : pairedDevices) {
							// Add the name and address to an array adapter to
							// show in a ListView
							System.out.println("found");
							String name = device.getName();
							String Address = device.getAddress();
							Button hostBtn = new Button(getApplicationContext());
							hostBtn.setText(name);
							hostBtn.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View arg0) {

									GameEnvironment.get().getPlayerInfo()
											.setServer(false);
									GameEnvironment.get().getBluetoothInfo()
											.setHostDevice(device);
									GameEnvironment
											.get()
											.getPlayerInfo()
											.setUsername(
													username.getText()
															.toString());

									Intent i = new Intent(
											CarddeckplatformActivity.this,
											GameActivity.class);
									startActivity(i);
									dialog.dismiss();
								}
							});
							ll.addView(hostBtn);
						}
					}

				}
				dialog.show();

			}
		});

		Button prefsBtn = (Button) findViewById(R.id.optionsButton);
		prefsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getBaseContext(), PrefsActivity.class);
				startActivity(i);
			}
		});

		Button market = (Button) findViewById(R.id.market);
		market.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!haveInternet(getContext()))
					showNoConnectionDialog(getContext(), MARKET);
				else{
					Intent i = new Intent(getBaseContext(),
							MarketActivity.class);
					startActivity(i);
					
				}
			}
		});

		Button rank = (Button) findViewById(R.id.rate);
		rank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!haveInternet(getContext()))
					showNoConnectionDialog(getContext(), PLUGINRANK);
				else{
					Intent i = new Intent(getBaseContext(),
							RankingActivity.class);
					startActivity(i);
					
				}
			}
		});

	}

	/**
	 * Checks if we have a valid Internet Connection on the device.
	 * 
	 * @param ctx
	 * @return True if device has internet
	 * 
	 *         Code from: http://www.androidsnippets.org/snippets/131/
	 */
	public static boolean haveInternet(Context ctx) {
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();

		if (info == null || !info.isConnected()) {
			return false;
		}
		if (info.isRoaming()) {
			// here is the roaming option you can change it if you want to
			// disable internet while roaming, just return false
			return true;
		}
		return true;
	}

	/**
	 * Display a dialog that user has no internet connection
	 * 
	 * @param ctx1
	 * 
	 *            Code from:
	 *            http://osdir.com/ml/Android-Developers/2009-11/msg05044.html
	 */
	public void showNoConnectionDialog(Context ctx1, final int requestCode) {
		final Context ctx = ctx1;
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setCancelable(true);
		builder.setMessage(R.string.no_connection);
		builder.setTitle(R.string.no_connection_title);
		builder.setPositiveButton(R.string.settings,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						startActivityForResult(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS), requestCode);
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				return;
			}
		});

		builder.show();
	}	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		if(resultCode==RESULT_OK){
			if(resultCode==this.MARKET){
				Intent i = new Intent(getBaseContext(),
						MarketActivity.class);
				startActivity(i);
			}
			else if(resultCode==this.PLUGINRANK){
				Intent i = new Intent(getBaseContext(),
						RankingActivity.class);
				startActivity(i);
				
			}
			else if(resultCode==this.FINDHOSTS){
				HostFinder hostFinder = new HostFinder();
				hostFinder.addObserver(availableHosts);
				hostFinder.findHosts();
			}
			
			
//		}
		
	}
}
