package ryan.versememorizer;

/**
 * Created by Ryan on 9/1/2015.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

//package org.esv.bible.service;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class ESVService extends AsyncTask<String,Integer,String> {

    //first parameter is the book second is the chapter and verses
    //if sent "daily" get the daily verse
    @Override
    protected String doInBackground(String... params){
        StringBuilder urlStringBuilder = new StringBuilder();
        StringBuilder outStringBuilder = new StringBuilder();
        try {
            if(params[0] != "daily") {
                urlStringBuilder.append("http://www.esvapi.org/v2/rest/passageQuery");
                urlStringBuilder.append("?key=IP");
                urlStringBuilder.append("&passage="
                        + URLEncoder.encode(params[0] + " " + params[1], "ISO-8859-1"));

                urlStringBuilder.append("&include-headings=true");
                urlStringBuilder.append("&output-format=plain-text&line-length=36&include-heading-horizontal-lines=0&include-first-verse-numbers=0");
                urlStringBuilder.append("&include-passage-horizontal-lines=0&include-short-copyright=0&include-footnotes=0&include-headings=0&include-selahs=0");
            }
            else
            {
                urlStringBuilder.append("http://www.esvapi.org/v2/rest/dailyVerse");
                urlStringBuilder.append("?key=IP");
                urlStringBuilder.append("&include-headings=true");
                urlStringBuilder.append("&output-format=plain-text&line-length=36&include-heading-horizontal-lines=0&include-first-verse-numbers=0");
                urlStringBuilder.append("&include-passage-horizontal-lines=0&include-short-copyright=0&include-footnotes=0&include-headings=0&include-selahs=0");

            }
            InputStream esvStream = new URL(urlStringBuilder.toString()).openStream();


            int nextChar;

            while ((nextChar = esvStream.read()) != -1) {
                outStringBuilder.append((char) nextChar);
            }

            esvStream.close();

        }catch (Exception ex)
        {
            Log.v("exception",ex.toString()); //alert need internet!!!
        }
        return outStringBuilder.toString();
    }
}
