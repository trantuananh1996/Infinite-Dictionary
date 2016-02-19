package com.pr.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleTranslator{

	private final String googleTranslatorURL = "https://translate.google.com/translate_a/single?client=t";
	private final String URLExtend = "&hl=vi&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&dt=at&ie=UTF-8&oe=UTF-8&source=btn&srcrom=1&ssel=0&tsel=0&kc=0&tk=886555|763496&q=";
	private String srcLang = "en";
	private String destLang = "vi";
	private String userAgent = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";

	public String translate(String query) throws MalformedURLException, IOException{
		String meaning = "";
		try{
			URL url = new URL(this.buildURLRequestWith(query));
			HttpURLConnection Conn = (HttpURLConnection) url.openConnection();
			Conn.addRequestProperty("User-Agent", this.userAgent);
			Conn.setRequestMethod("GET");
			Conn.setConnectTimeout(30000);
			Conn.connect();
			InputStream inputStream = Conn.getInputStream();
			BufferedReader BuffInputStream = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String respContent = BuffInputStream.readLine();
			inputStream.close();
			BuffInputStream.close();
			Conn.disconnect();
			String result[] = respContent.split("[,]");
			for (String r : result){
				// remove "
				r = r.trim().replaceAll("\"", "");
				r.replace("\\]", "-");
				// remove [ and ]
				char[] tmp = r.toCharArray();
				for (int i = 0; i < tmp.length; i++){
					if (tmp[i] == '[' || tmp[i] == ']') tmp[i] = ' ';
				}
				r = String.valueOf(tmp);
				r = r.trim();
				meaning = meaning + r;
				break;
			}
		}catch(MalformedURLException e){
			meaning = "Đã xảy ra lỗi!";
		}
		// return respContent;
		if ( meaning == "null" || meaning == "") return "Đã xảy ra lỗi!";
		else return meaning;
	}

	// build URL Request
	private String buildURLRequestWith(String query){
		String urlBuilder = "";
		urlBuilder += this.googleTranslatorURL;
		urlBuilder += "&sl=" + this.srcLang;
		urlBuilder += "&tl=" + this.destLang;
		urlBuilder += this.URLExtend;

		String queryEncoded = "";
		// Encode query to UTF-8
		try{
			queryEncoded = URLEncoder.encode(query, "UTF-8");
		}catch (Exception e){
		}
		urlBuilder += queryEncoded;
		return urlBuilder;
	}

	// getter and setter
	public String getSrcLang(){
		return srcLang;
	}

	public void setSrcLang(String srcLang){
		this.srcLang = srcLang;
	}

	public String getDestLang(){
		return destLang;
	}

	public void setDestLang(String destLang){
		this.destLang = destLang;
	}

	public String getUserAgent(){
		return userAgent;
	}

	public void setUserAgent(String userAgent){
		this.userAgent = userAgent;
	}
}
