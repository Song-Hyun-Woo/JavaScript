package com.web.board.model.vo;

import java.sql.Date;
import java.util.List;

import com.web.member.model.vo.Member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Board {
	private int boardNo;
	private String boardTitle;
	private String boardWriter;
//	private Member boardWriter;
	private String boardContent;
	private String boardOriginalFilename;
	private String boardRenamedFilename;
	private Date boardDate;
	private int boardReadcount;
//	private List<BoardComment> comments;
	
}
