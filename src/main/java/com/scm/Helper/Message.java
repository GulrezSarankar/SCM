package com.scm.Helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Message {
	
	private String content;
	
	@Builder.Default
	private messageType type=messageType.blue;
	
	


	
}
