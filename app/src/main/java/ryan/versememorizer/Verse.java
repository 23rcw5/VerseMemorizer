package ryan.versememorizer;

import android.app.Activity;
import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ryan on 9/1/2015.
 */
public class Verse {
    public Verse(String Book, String chapter, String verseStart, String verseEnd, Context context)throws XmlPullParserException, IOException {
        XmlPullParserFactory factory =  XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        InputStream in_s = context.getAssets().open("esvbible.xml");
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in_s, null);
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name == "product"){
                        //currentProduct = new Product();
                    } //else if (currentProduct != null){
                    if (name == "productname"){
                        //currentProduct.name = parser.nextText();
                    } else if (name == "productcolor"){
                        // currentProduct.color = parser.nextText();
                    } else if (name == "productquantity"){
                        // currentProduct.quantity= parser.nextText();
                    }
                    //}
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    //if (name.equalsIgnoreCase("product") && currentProduct != null){
                    //products.add(currentProduct);
                    // }
            }
            eventType = parser.next();
        }

    }

}
