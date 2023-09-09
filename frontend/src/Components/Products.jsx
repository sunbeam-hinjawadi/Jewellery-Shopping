import styled from "styled-components";
import { popularProducts } from "../data";
import Product from "./Product";
import { useEffect, useState } from "react";
import axios from "axios";

const Container = styled.div`
	padding: 20px;
	display: flex;
	flex-wrap: wrap;
	justify-content: space-between;
`;

const Products = ({ filter }) => {
	useEffect(() => {
		getAllProducts();
	}, []);
	const getAllProducts = async () => {
		try {
			const res = await axios.get("http://localhost:8085/api/v1/jewellery");
			console.log(res);
		} catch (error) {
			console.log(error);
		}
	};
	return (
		<Container>
			{popularProducts.map((item) => (
				<Product item={item} key={item.id} />
			))}
		</Container>
	);
};

export default Products;
