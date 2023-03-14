import styled from "styled-components";

export const JoinAgeContainer = styled.div`
	height: 100vh;

	display: flex;
	flex-direction: column;

	padding: 0 16px;

	padding-top: 56px;
`;

export const GendersContainer = styled.div`
	display: flex;

	flex: 1;
	flex-direction: column;
	justify-content: center;
`;
export const GenderContainer = styled.div`
	text-align: center;

	margin-bottom: 42px;
`;

export const GenderImgWrapper = styled.div`
	width: 120px;
	height: 120px;

	margin: auto;
	padding: 16px;

	display: flex;
	justify-content: center;

	background: var(--gray-50);

	border: 0.8px solid var(--gray-300);
	border-radius: 50%;

	:active {
		background: var(--gray-100);
	}
`;
