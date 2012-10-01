package utils;


public class CardTransformation {
	
	//-------Singleton implementation--------//
		private static class CardTransformationHolder
		{
			private final static CardTransformation cardTransformationHolder=new CardTransformation();
		}
					
							
		/**
		 * get Client Bitmap Holder instance
		 */
		public static CardTransformation get(){
			return CardTransformationHolder.cardTransformationHolder;
		}
		
		private CardTransformation() {
			
		}

		public void transform(Card card,Position oldPos,Position newPos) {
			// TODO Auto-generated method stub
			
		}
}
