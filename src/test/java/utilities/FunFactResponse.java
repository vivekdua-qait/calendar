package utilities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FunFactResponse {

	@JsonProperty("fact")
	private String fact;
	@JsonProperty("length")
	private int length;

	@JsonProperty("fact")
	public String getFact() {
		return fact;
	}

	@JsonProperty("length")
	public int getLength() {
		return length;
	}
}
