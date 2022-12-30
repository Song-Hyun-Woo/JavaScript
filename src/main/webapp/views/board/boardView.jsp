<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.web.board.model.vo.*" %>    
<%
	Board b=(Board)request.getAttribute("board");
	List<BoardComment> comments=(List<BoardComment>)request.getAttribute("comments");
%>
<%@ include file="/views/common/header.jsp"%>
<style>
    section#board-container{width:600px; margin:0 auto; text-align:center;}
    section#board-container h2{margin:10px 0;}
    table#tbl-board{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-board th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-board td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
    div#comment-container button#btn-insert{width:60px;height:50px; color:white;
    background-color:#3300FF;position:relative;top:-20px;}
     /*댓글테이블*/
    table#tbl-comment{width:580px; margin:0 auto; border-collapse:collapse; clear:both; } 
    table#tbl-comment tr td{border-bottom:1px solid; border-top:1px solid; padding:5px; text-align:left; line-height:120%;}
    table#tbl-comment tr td:first-of-type{padding: 5px 5px 5px 50px;}
    table#tbl-comment tr td:last-of-type {text-align:right; width: 100px;}
    table#tbl-comment button.btn-reply{display:none;}
    table#tbl-comment button.btn-delete{display:none;}
    table#tbl-comment tr:hover {background:lightgray;}
    table#tbl-comment tr:hover button.btn-reply{display:inline;}
    table#tbl-comment tr:hover button.btn-delete{display:inline;}
    table#tbl-comment tr.level2 {color:gray; font-size: 14px;}
    table#tbl-comment sub.comment-writer {color:navy; font-size:14px}
    table#tbl-comment sub.comment-date {color:tomato; font-size:10px}
    table#tbl-comment tr.level2 td:first-of-type{padding-left:100px;}
    table#tbl-comment tr.level2 sub.comment-writer {color:#8e8eff; font-size:14px}
    table#tbl-comment tr.level2 sub.comment-date {color:#ff9c8a; font-size:10px}
    /*답글관련*/
    table#tbl-comment textarea{margin: 4px 0 0 0;}
    table#tbl-comment button.btn-insert2{width:60px; height:23px; color:white; background:#3300ff; position:relative; top:-5px; left:10px;}
</style>
   
		<section id="board-container">
		<h2>게시판</h2>
		<table id="tbl-board">
			<tr>
				<th>글번호</th>
				<td><%=b.getBoardNo() %></td>
			</tr>
			<tr>
				<th>제 목</th>
				<td><%=b.getBoardTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%=b.getBoardWriter() %></td>
			</tr>
			<tr>
				<th>조회수</th>
				<td><%=b.getBoardReadcount() %></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
				 <%if(b.getBoardOriginalFilename()!=null){ %>
				 	<img src="<%=request.getContextPath()%>/imgaes/file.png"
				 	width="20"/>
				 <%}else{ %>
				 	첨부파일 없음
				 <%} %>
				</td>
			</tr>
			<tr>
				<th>내 용</th>
				<td><%=b.getBoardContent() %></td>
			</tr>
			<%--글작성자/관리자인경우 수정삭제 가능 --%>
			<%if(loginMember!=null
				&&(loginMember.getUserId().equals("admin")
						||loginMember.getUserId().equals(b.getBoardWriter()))){ %>
			<tr>
				<th colspan="2">
					<button >수정하기</button>
					<button >삭제하기</button>
				</th>
			</tr>
			<%} %>			
		</table>
   	<div id="comment-container">
   		<div class="comment-editor">
   			<form action="<%=request.getContextPath() %>/board/commentWrite.do" 
   			method="post">
   				<textarea name="content" cols="55" rows="3"></textarea>
   				<input type="hidden" name="boardref" value="<%=b.getBoardNo()%>">
   				<input type="hidden" name="level" value="1"/>
   				<input type="hidden" name="commentref" value="0"/>
   				<input type="hidden" name="commentWriter" value="<%=loginMember!=null?loginMember.getUserId():""%>">   				
   				<button	type="submit" id="btn-insert">등록</button>
   			</form>
   		</div>
   		<table id="tbl-comment">
   			<%if(!comments.isEmpty()){ 
   				for(BoardComment bc : comments){
   				if(bc.getBoardCommentLevel()==1){%>
   				<tr class="level1">
   					<td>
   						<sub class="comment-writer"><%=bc.getBoardCommentWriter() %></sub>
   						<sub class="comment-date"><%=bc.getBoardCommentDate() %></sub>
   						<br>
   						<%=bc.getBoardCommentContent() %>
   					</td>		
   					<td>
   						<%if(loginMember!=null){ %>
   							<button class="btn-reply" value="<%=bc.getBoardCommentNo() %>">답글</button>
   						<%} %>
   						<%if(loginMember!=null&&
   							(loginMember.getUserId().equals("admin")||
   							loginMember.getUserId().equals(bc.getBoardCommentWriter()))) {%>
   							<button class="btn-delete">삭제</button>
   						<%} %>
   					</td>
   				</tr>
   			<%} else{%>
   				<tr class="level2">
   					<td>
   						<sub><%=bc.getBoardCommentWriter() %></sub>
   						<sub><%=bc.getBoardCommentDate() %></sub>
   						<br>
   						<%=bc.getBoardCommentContent() %>
   					</td>
   					<td>
   					</td>
   				</tr>
   			<% }
   			}//for
   		}//if%>
   		</table>	
   	</div>
    </section>
    <script>
    	$(()=>{
    		$(".comment-editor>form>textarea").focus(e=>{
    			if(<%=loginMember==null%>){
    				alert("로그인 후 이용할 수 있습니다.");
    				$("#userId").focus();
    			}
    		});
    		$(".btn-reply").click(e=>{
    			const tr=$("<tr>");
    			const form=$(".comment-editor>form").clone();
    			form.find("textarea").attr({"rows":"1"});
    			form.find("input[name=level]").val("2");
    			form.find("input[name=commentref]").val($(e.target).val());
    			form.find("button").removeAttr("id").addClass("btn-insert2");
    			const td=$("<td>").attr("colspan","2").append(form);
    			tr.append(td);
    			tr.find("td").css("display","none");
    			tr.insertAfter($(e.target).parents("tr")).children("td").slideDown(800);
    			$(e.target).off("click");
    		});
    	})
    </script>
<%@ include file="/views/common/footer.jsp"%>