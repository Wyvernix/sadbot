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
		String url = "http://www.youtube.com/watch?v="+videotag;
		
		Document doc = Jsoup.parse(loadHtml(url));
		
		String title = doc.select("span[id=eow-title]").first().attr("title");
		String user = doc.select("a[class*=g-hovercard]").first().ownText();
		
		if (title.equals("null")) {
			return null;
		}
		
    	return title + "' by " + user;
    	//<h1 id="watch-headline-title" class="yt">
    	//<span title="Caramell - Caramelldansen (English Version) Official" dir="ltr" class="watch-title long-title yt-uix-expander-head" id="eow-title"> Caramell - Caramelldansen (English Version) Official</span>
    }
    
    //stupid parser wont load ムラサキ
    public static String loadHtml(String urlp) {
    	String str = "cake";
    	InputStream is = null;
    	try {
    		HttpURLConnection connection = (HttpURLConnection) new URL(urlp).openConnection();
    		is = connection.getInputStream();
    		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
    		str = readAll(rd);
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
    	return str;
    }
    
    public static String getTweet(String url) {
    	//url likely https%3A%2F%2Ftwitter.com%2Fintent%2Ftweet%3Fstatus%3DHang%2Bout%2Bwith%2Bme%2Bat%2Btwitch.tv%252F
    	String response = null;
    	
    	/////////////
    	
    	JsonElement je = null;
    	InputStream is = null;
    	String outo = "";
//        String outo = "null";
        try {
        	HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        	is = connection.getInputStream();
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

            je = new JsonParser().parse(jsonText);
            
            if (je != null) {
    			outo = getAtPath(getAtPath(je, "data"), "url").getAsString();
    			
    			response = "Tweet out the stream! "+outo;
    		}
            
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
        
    	/////////////
        
    	
    	return response;
    }
}



/*
 * 
 * 
 * 
*/
