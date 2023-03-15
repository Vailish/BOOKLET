import React from "react";
import { Text } from "../../../styles/common/TextsStyle";
import {
	PreviewSentenceBottonContent,
	PreviewSentenceContainer,
	PreviewSentenceContent,
} from "../../../styles/feed/PostPreviewStyle";

function PreviewSentence({ post }) {
	return (
		<PreviewSentenceContainer color={post.paragraphColor}>
			<PreviewSentenceContent>
				<Text font="jeju" height="28">
					{post.content}
				</Text>
				<PreviewSentenceBottonContent>
					<div>
						<Text size="12" weight="bold" marginBottom="4">
							{post.bookTitle}
						</Text>
						<Text size="12">{post.bookAuthor}</Text>
					</div>
					<Text size="12">P. {post.paragraphPage}</Text>
				</PreviewSentenceBottonContent>
			</PreviewSentenceContent>
		</PreviewSentenceContainer>
	);
}

export default PreviewSentence;
