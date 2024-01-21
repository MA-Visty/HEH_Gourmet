import { NavLink } from "react-router-dom";
import React, {useState} from "react";
import { Card, Row, Col } from 'react-bootstrap';
import ProductItemForm from "./ProductItemForm";
import ProductItemFavorite from "./ProductItemFavorite";
import {useAppContext} from "../../store/AppContext";

function ProductItem({product}) {
	const { state } = useAppContext();

	return (
		<Card
			style={{margin: 5}}
			id={product.id}
			onDragStart={(e) => {e.preventDefault();}}
			style={{
				MozUserSelect: "none",
				WebkitUserSelect: "none",
				msUserSelect: "none"
		}}>
			{state.user !== "" ? <ProductItemFavorite product={product} /> : <></> }
			<NavLink to={`/product/${product.id}`}>
				<Card.Img style={{ objectFit: 'cover', height: '250px', borderBottom: '1px solid #dee2e6'}} variant="top" src={product.imageUrl} />
				<Card.Body>
					<Row className="justify-content-between">
						<Col xs="auto">
							<Card.Title>{product.name}</Card.Title>
						</Col>
						<Col xs="auto">
							{product.price} €
						</Col>
					</Row>
				</Card.Body>
			</NavLink>
			<Card.Footer className="text-muted">
				<ProductItemForm product={product}/>
			</Card.Footer>
		</Card>
	);
}

export default ProductItem;