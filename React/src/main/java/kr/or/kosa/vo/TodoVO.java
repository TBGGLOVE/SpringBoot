package kr.or.kosa.vo;

import lombok.Data;

@Data
public class TodoVO {
	private int id;
	private String title;
	private char checked;
	private String regDate;
	private String modDate;

}
