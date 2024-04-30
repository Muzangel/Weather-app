package com.example.angel_s2110961.Adapter;

import android.util.Log;
import android.util.Xml;

import com.example.angel_s2110961.Domains.Forecast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CityParser {

    private static final String ns = null;

    public List<Forecast> parse(InputStream in) throws XmlPullParserException, IOException {
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

    private List<Forecast> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Forecast> forecasts = new ArrayList<>();

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
                        forecasts.add(readItem(parser));
                    } else {
                        skip(parser);
                    }
                }
            } else {
                skip(parser);
            }
        }
        return forecasts;
    }

    private Forecast readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
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

        Forecast forecast = new Forecast();
        forecast.setStatus(extractStatus(title));
        forecast.setCityName(extractCityName(title));
        forecast.setDate(extractDay(title));
        forecast.setTemperature(extractTemperature(title));
        forecast.setMaxTemperature(extractMaxTemperature(description));
        forecast.setMinTemperature(extractMinTemperature(description));

        Log.d("PullParser", "Parsed Forecast: " + forecast.toString());

        return forecast;
    }

    // ... (The rest of the code remains the same) ...


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

    private String extractCityName(String title) {
        String[] parts = title.split(":");
        if (parts.length > 0) {
            return parts[0].trim();
        }
        return "";
    }

    private String extractDay(String title) {
        // Extract the day from the title (e.g., "Tuesday" from "Tuesday: Light Cloud, Minimum Temperature: ...")
        return title.split(":")[0].trim();
    }


    private String extractStatus(String title) {
        return title.split(":")[1].split(",")[0].trim();
//        String[] parts = title.split(":")[1].split(",")[0].trim();
//        if (parts.length > 1) {
//            return parts[1].trim();
//        }
//        return "";
    }


    private String extractTemperature(String title) {
        if (title != null) {
            String[] parts = title.split("Maximum Temperature:");
            if (parts.length > 1) {
                return parts[1].trim().split("\\)")[0].trim();
            }
        }
        return "";
    }

    private String extractMinTemperature(String title) {

        if (title != null) {
            String[] temperatureParts = title.split("Minimum Temperature:");
            String temp = temperatureParts[1].split(",")[0].trim().split(" ")[0];
            return temp;
//            String[] parts = title.split(":");
//            if (parts.length > 2) {
//                String temperaturePart = parts[2].trim();
//                String[] temperatureParts = temperaturePart.split("Minimum Temperature");
//                if (temperatureParts.length > 0) {
//                    return temperatureParts[0].trim();
//                }
//            }
        }
        return "";
    }

    private String extractMaxTemperature(String title) {
        if (title != null) {
            String[] temperatureParts = title.split("Maximum Temperature:");
            String temp = temperatureParts[1].split(",")[0].trim().split(" ")[0];
            return temp;
//            String[] parts = title.split(":");
//            if (parts.length > 2) {
//                String temperaturePart = parts[2].trim();
//                String[] temperatureParts = temperaturePart.split("Maximum Temperature");
//                if (temperatureParts.length > 1) {
//                    return temperatureParts[1].trim();
//                }
//            }
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

    private String extractPressure(String description) {
        String[] parts = description.split(",");
        for (String part : parts) {
            if (part.contains("Pressure")) {
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
}