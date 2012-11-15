package org.me.joker;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses XML feeds from stackoverflow.com.
 * Given an InputStream representation of a feed, it returns a List of entries,
 * where each list element represents a single entry (post) in the XML feed.
 */
public class XMLParser {
    private static final String ns = null;

    // We don't use namespaces

    public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
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

    private List<Entry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Entry> entries = new ArrayList<Entry>();

        parser.require(XmlPullParser.START_TAG, ns, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    // This class represents a single entry (post) in the XML feed.
    // It includes the data members "guid," "voteUp," and "voteDown."
    public static class Entry {
        public final String guid;
        public final String voteUp;
        public final String voteDown;

        private Entry(String guid, String voteDown, String voteUp) {
            this.guid = guid;
            this.voteDown = voteDown;
            this.voteUp = voteUp;
        }
    }

    // Parses the contents of an entry. If it encounters a guid, voteDown, or voteUp tag, hands them
    // off
    // to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");
        String guid = null;
        String voteDown = null;
        String voteUp = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("guid")) {
                guid = readGuid(parser);
            } else if (name.equals("voteDown")) {
                voteDown = readVoteDown(parser);
            } else if (name.equals("voteUp")) {
                voteUp = readVoteUp(parser);
            } else {
                skip(parser);
            }
        }
        return new Entry(guid, voteDown, voteUp);
    }

    // Processes guid tags in the feed.
    private String readGuid(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "guid");
        String guid = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "guid");
        return guid;
    }

    // Processes voteUp tags in the feed.
    private String readVoteUp(XmlPullParser parser) throws IOException, XmlPullParserException {
        String voteUp = "";
        parser.require(XmlPullParser.START_TAG, ns, "voteUp");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (tag.equals("voteUp")) {
            if (relType.equals("alternate")) {
                voteUp = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "voteUp");
        return voteUp;
    }

    // Processes voteDown tags in the feed.
    private String readVoteDown(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "voteDown");
        String voteDown = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "voteDown");
        return voteDown;
    }

    // For the tags guid and voteDown, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
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
}
