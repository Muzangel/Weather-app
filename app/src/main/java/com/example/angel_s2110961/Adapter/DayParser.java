package com.example.angel_s2110961.Adapter;

import android.util.Log;
import android.util.Xml;

import com.example.angel_s2110961.Domains.DayDomain;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DayParser {

    private static final String ns = null;

    public List<DayDomain> parse(InputStream in) throws XmlPullParserException, IOException {
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

    private List<DayDomain> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<DayDomain> days = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next()!= XmlPullParser.END_TAG) {
            if (parser.getEventType()!= XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("channel")) {
                parser.require(XmlPullParser.START_TAG, ns, "channel");
                while (parser.next()!= XmlPullParser.END_TAG) {
                    if (parser.getEventType()!= XmlPullParser.START_TAG) {
                        continue;
                    }
                    name = parser.getName();
                    if (name.equals("item")) {
                        days.add(readItem(parser));
                    } else {
                        skip(parser);
                    }
                }
            } else {
                skip(parser);
            }
        }
        return days;
    }

    private DayDomain readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        while (parser.next()!= XmlPullParser.END_TAG) {
            if (parser.getEventType()!= XmlPullParser.START_TAG) {
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

        DayDomain day = new DayDomain(
                description,
                extractDate(title),
                formatDescription(description),
                formatWindSpeed(description),
                formatPressure(description),
                formatSunset(description),
                formatVisibility(description),
                formatHumidity(description)
        );


        Log.d("PullParser", "Parsed Day: " + day.toString());

        return day;
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
        if (parser.getEventType()!= XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth!= 0) {
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

    // Formatting methods from the first snippet
    private String formatTemperature(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Minimum Temperature")) {
                return "Temperature: " + part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String formatDescription(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Minimum Temperature")) {
                return "Description: " + part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String formatWindSpeed(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Wind Speed")) {
                return "Wind Speed: " + part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String formatPressure(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Pressure")) {
                return "Pressure: " + part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String formatVisibility(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Visibility")) {
                return "Visibility: " + part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String formatHumidity(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Humidity")) {
                return "Humidity: " + part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String formatUVRisk(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("UV Risk")) {
                return "UV Risk: " + part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String formatSunset(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.trim().startsWith("Sunset:")) {
                return "Sunset: " + part.trim().split(":")[1];
            }
        }
        return "";
    }

    private String formatWindDirection(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Wind Direction")) {
                return "Wind Direction: " + part.split(":")[1].trim();
            }
        }
        return "";
    }

    private String formatPollution(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Pollution")) {
                return "Pollution: " + part.split(":")[1].trim();
            }
        }
        return "";
    }

    // Placeholder for extractDate method
    private String extractDate(String title) {
        // Implement date extraction logic here
        return ""; // Return an empty string or a default value
    }
}
