package chat;


import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaMessage {
	@XStreamAlias("MediaId")
	@XStreamCDATA
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
