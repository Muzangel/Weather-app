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

        try {
            weather.setDescription(extractDescription(title));
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e("PullParser", "Error extracting description", e);
            // Set a default value or handle the error as needed
            weather.setDescription("");
        }

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
        String[] parts = title.split(",");
        for (String part : parts) {
            if (part.contains("Temperature")) {
                return part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String extractDescription(String title) {
        String[] parts = title.split(",");
        if (parts.length > 0) {
            return parts[0].trim();
        }
        return "";
    }

    private String extractWindDirection(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Wind Direction")) {
                return part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String extractWindSpeed(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Wind Speed")) {
                return part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String extractVisibility(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Visibility")) {
                return part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String extractPressure(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Pressure")) {
                return part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String extractHumidity(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Humidity")) {
                return part.split(":")[1].trim();
            }
        }
        return "";
    }
}