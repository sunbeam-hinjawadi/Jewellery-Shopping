import {
	Facebook,
	Instagram,
	MailOutline,
	Phone,
	Pinterest,
	Room,
	Twitter
} from "@mui/icons-material";
import styled from "styled-components";
import { mobile } from "../responsive";
import { Link } from "react-router-dom";

const Container = styled.div`
	display: flex;
	${mobile({ flexDirection: "column" })}
`;
const Left = styled.div`
	flex: 1;
	display: flex;
	flex-direction: column;
	padding: 20px;
`;
const Logo = styled.h1``;
const Desc = styled.p`
	margin: 20px 0px;
`;
const SocialContainer = styled.div`
	display: flex;
`;
const SocialIcon = styled.div`
	width: 40px;
	height: 40px;
	border-radius: 50%;
	color: white;
	background-color: #${(props) => props.color};
	display: flex;
	align-items: center;
	justify-content: center;
	margin-right: 20px;
`;

const Center = styled.div`
	flex: 1;
	padding: 20px;
	${mobile({ display: "none" })}
`;
const Title = styled.h3`
	margin-bottom: 30px;
`;
const List = styled.ul`
	margin: 0;
	padding: 0;
	list-style: none;
	display: flex;
	flex-wrap: wrap;
`;
const ListItem = styled.li`
	width: 50%;
	margin-bottom: 10px;
`;
const Right = styled.div`
	flex: 1;
	padding: 20px;
	${mobile({ backgroundColor: "#fff8f8" })}
`;
const ContactItem = styled.div`
	margin-bottom: 20px;
	display: flex;
	align-items: center;
`;
const Payment = styled.img`
	width: 50%;
`;
const Footer = () => {
	return (
		<Container>
			<Left>
				<Logo>
					<Link className="logo-link" to="/">
						JSMS.
					</Link>
				</Logo>
				<Desc>
					Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellendus
					assumenda ipsam fugit esse ipsum harum nostrum? Dolor aliquid eveniet
					hic.
				</Desc>
				<SocialContainer>
					<SocialIcon color="3B5999">
						<Facebook />
					</SocialIcon>
					<SocialIcon color="E4405F">
						<Instagram />
					</SocialIcon>
					<SocialIcon color="55ACEE">
						<Twitter />
					</SocialIcon>
					<SocialIcon color="E60023">
						<Pinterest />
					</SocialIcon>
				</SocialContainer>
			</Left>
			<Center>
				<Title>Useful Links</Title>
				<List>
					<ListItem>Home</ListItem>
					<ListItem>Delivery Information</ListItem>
					<ListItem>Payment Options</ListItem>
					<ListItem>Blogs</ListItem>
					<ListItem>Offers & Contents</ListItem>
					<ListItem>Order Tracking</ListItem>
					<ListItem>Help & FAQs</ListItem>
					<ListItem>Terms</ListItem>
				</List>
			</Center>
			<Right>
				<Title>Contact</Title>
				<ContactItem>
					<Room style={{ marginRight: "10px" }} /> 24, Raka Colony, Vakilpeth,
					Nagpur-09
				</ContactItem>
				<ContactItem>
					<Phone style={{ marginRight: "10px" }} /> +91 8390830181
				</ContactItem>
				<ContactItem>
					<MailOutline style={{ marginRight: "10px" }} /> contact@jsms.shop
				</ContactItem>
				<Payment src="https://i.ibb.co/Qfvn4z6/payment.png" />
			</Right>
		</Container>
	);
};

export default Footer;
