import React from "react";
import { Container } from "../../../styles/common/ContainingsStyle";
import { Span, Text } from "../../../styles/common/TextsStyle";
import CommentInput from "../../molecules/Input/CommentInput";
import CommentList from "../../molecules/Sentence/CommentList";

function DetailComment({ comments }) {
	return (
		<Container marginTop="32" paddingLeft="16" paddingRight="16">
			<Container marginBottom="24">
				<Span size="19" weight="bold">
					댓글
				</Span>
				<Span size="19" color="var(--gray-200)" marginLeft="8" marginRight="8">
					|
				</Span>
				<Span size="19">{comments.length}</Span>
			</Container>
			<CommentInput />
			<CommentList comments={comments} />
		</Container>
	);
}

export default DetailComment;
