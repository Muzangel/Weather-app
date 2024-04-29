package com.example.angel_s2110961.Adapter;

import android.util.Log;
import android.util.Xml;

import com.example.angel_s2110961.Domains.Weather;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PullParser {

    private static final String ns = null;

    public List<Weather> parse(InputStream in) throws XmlPullParserException, IOException {
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

    private List<Weather> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Weather> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("channel")) {
                parser.require(XmlPullParser.START_TAG, ns, "channel");
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    name = parser.getName();
                    if (name.equals("item")) {
                        entries.add(readEntry(parser));
                    } else {
                        skip(parser);
                    }
                }
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private Weather readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        String pubDate = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readTitle(parser);
                    break;
                case "description":
                    description = readDescription(parser);
                    break;
                case "pubDate":
                    pubDate = readPubDate(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }

        Weather weather = new Weather();
        weather.setCityName(getCityName(title));
        weather.setDate(pubDate);
        weather.setTemperature(getTemperature(title));
        weather.setMinTemp(getMinTemp(title));
        weather.setMaxTemp(getMaxTemp(title));

        Log.d("PullParser", "Parsed Weather: " + weather.toString());

        return weather;
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }

    private String readPubDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String pubDate = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return pubDate;
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

    private String getCityName(String title) {
        // Extract city name from the title
        String[] parts = title.split(":");
        if (parts.length > 0) {
            return parts[0].trim();
        }
        return "";
    }

    private String getTemperature(String title) {
        // Extract temperature from the title
        String[] parts = title.split(",");
        if (parts.length > 1) {
            String temperaturePart = parts[1].trim();
            String[] temperatureParts = temperaturePart.split(":");
            if (temperatureParts.length > 1) {
                return temperatureParts[1].trim();
            }
        }
        return "";
    }

    private String getMinTemp(String title) {
        // Extract minimum temperature from the title
        String[] parts = title.split(",");
        if (parts.length > 1) {
            String minTempPart = parts[1].trim();
            String[] minTempParts = minTempPart.split(":");
            if (minTempParts.length > 1) {
                return minTempParts[1].trim().replaceAll("[^0-9]", "");
            }
        }
        return "";
    }

    private String getMaxTemp(String title) {
        // Extract maximum temperature from the title
        String[] parts = title.split(",");
        if (parts.length > 2) {
            String maxTempPart = parts[2].trim();
            String[] maxTempParts = maxTempPart.split(":");
            if (maxTempParts.length > 1) {
                return maxTempParts[1].trim().replaceAll("[^0-9]", "");
            }
        }
        return "";
    }
}