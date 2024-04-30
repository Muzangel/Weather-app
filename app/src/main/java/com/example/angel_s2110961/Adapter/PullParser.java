package com.example.angel_s2110961.Adapter;

import android.util.Log;
import android.util.Xml;

import com.example.angel_s2110961.Domains.Weather;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class PullParser {

    private static final String ns = null;

    public Weather parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private Weather readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        Weather weather = null;

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("channel")) {
                weather = readChannel(parser);
            } else {
                skip(parser);
            }
        }
        return weather;
    }

    private Weather readChannel(XmlPullParser parser) throws XmlPullParserException, IOException {
        Weather weather = new Weather();

        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("item")) {
                weather = readItem(parser);
            } else {
                skip(parser);
            }
        }
        return weather;
    }

    private Weather readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readText(parser);
                    break;
                case "description":
                    description = readText(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        Weather weather = new Weather();
        weather.setTemperature(extractTemperature(title));
        weather.setDescription(extractDescription(title));
        weather.setWindDirection(extractWindDirection(description));

        Log.d("PullParser", "Parsed Weather: " + weather.toString());

        return weather;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String extractTemperature(String title) {
        // Extract temperature from the title
        String[] parts = title.split(",");
        if (parts.length > 1) {
            return parts[1].trim();
        }
        return "";
    }

    private String extractDescription(String channelDescription) {
        // Extract description from the channel description
        if (channelDescription != null) {
            String[] parts = channelDescription.split(",");
            if (parts.length > 0) {
                String weatherPart = parts[0].trim();
                int weatherIndex = weatherPart.indexOf("weather");
                if (weatherIndex != -1) {
                    return weatherPart.substring(weatherIndex + "weather".length()).trim();
                }
            }
        }
        return "";
    }

    private String extractWindDirection(String description) {
        // Extract wind direction from the description
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Wind Direction")) {
                return part.split(":")[1].trim();
            }
        }
        return "";
    }
}