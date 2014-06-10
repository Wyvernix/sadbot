package me.wyvernix.sadbot;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.*;


public class GSONic {
	private static String readAll(Reader rd) throws IOException {

        BufferedReader reader = new BufferedReader(rd);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private static JsonElement getAtPath(JsonElement e, String path) {
        JsonElement current = e;
        String[] ss = path.split("/");
        for (int i = 0; i < ss.length; i++) {
            current = current.getAsJsonObject().get(ss[i]);
        }
        return current;
    }

//    public static String getFollows(String url) {
//    	String outo = "null";
//    	try {
//    		JsonElement je = getTwitch(url);
//    		if (je != null) {
//    			outo = (getAtPath(je, "status").getAsString());
//    		}
//    	} catch(Exception e) {
//    		outo = "null";
//    	}
//    	return outo;
//    }
    
    
    public static String getStatus(String url) {
    	String outo = "null";
    	try {
    		JsonElement je = getTwitch(url);
    		if (je != null) {
    			outo = (getAtPath(je, "status").getAsString());
    		}
    	} catch(Exception e) {
    		outo = "null";
    	}
    	return outo;
    }
    
    public static boolean isStreaming(String url) {
    	JsonElement je = getTwitch(url);
		if (je != null) {
			return !getAtPath(je, "stream").isJsonNull();
		}
    	return false;
    }
    
    public static JsonElement getTwitch(String url) {
    	JsonElement je = null;
    	InputStream is = null;
//        String outo = "null";
        try {
        	HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        	connection.setRequestProperty("Client-ID", "df0s6sdx3ef3g1o20y1ovrqi5f6lc7o");
        	connection.addRequestProperty("Accept", "application/vnd.twitchtv.v2+json");
        	is = connection.getInputStream();
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

            je = new JsonParser().parse(jsonText);
//
//            try {
//            	outo = (getAtPath(je, "status").getAsString());
//            } catch(Exception e) {
//            	outo = "null";
//            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            newGUI.logError(e);
        } catch (IOException e) {
            e.printStackTrace();
            newGUI.logError(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                newGUI.logError(e);
            }
        }
        return je;
    }
    
    public static String getYoutube(String videotag) {
    	String title = "null";
    	String user = "null";
    	Document doc;
		try {
//			System.out.println(videotag);
//			System.out.println("http://www.youtube.com/watch?v="+videotag);
			//http://www.youtube.com/watch?v=A67ZkAd1wmI
			//http://www.youtube.com/watch?v=A67ZkAd1w
//			String url = "http://www.latijnengrieks.com/vertaling.php?id=5368";
//			Document document = Jsoup.parse(new URL(url).openStream(), "ISO-8859-1", url);
//			Element paragraph = document.select("div.kader p").first();
//
//			for (Node node : paragraph.childNodes()) {
//			    if (node instanceof TextNode) {
//			        System.out.println(((TextNode) node).text().trim());
//			    }
//			}
			String url = "http://www.youtube.com/watch?v="+videotag;
			
			doc = Jsoup.parse(loadHtml(url));
			
			title = doc.select("span[id=eow-title]").first().attr("title");
			user = doc.select("a[class*=g-hovercard]").first().ownText();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
		if (title.equals("null")) {
			return null;
		}
		
    	return title + "' by " + user;
    	//<h1 id="watch-headline-title" class="yt">
    	//<span title="Caramell - Caramelldansen (English Version) Official" dir="ltr" class="watch-title long-title yt-uix-expander-head" id="eow-title"> Caramell - Caramelldansen (English Version) Official</span>
    }
    
    //stupid parser wont load ムラサキ
    public static String loadHtml(String urlp) throws UnsupportedEncodingException, IOException {
    	URL url = new URL(urlp);
    	URLConnection con = url.openConnection();
//    	Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
//    	Matcher m = p.matcher(con.getContentType());
    	/* If Content-Type doesn't match this pre-conception, choose default and 
    	 * hope for the best. */
//    	String charset = m.matches() ? m.group(1) : "ISO-8859-1";
    	Reader r = new InputStreamReader(con.getInputStream(), "UTF-8");
    	StringBuilder buf = new StringBuilder();
    	while (true) {
    	  int ch = r.read();
    	  if (ch < 0)
    	    break;
    	  buf.append((char) ch);
    	}
    	String str = buf.toString();
    	return str;
    }
}



/*
 * 
 * 
 * 
*/
