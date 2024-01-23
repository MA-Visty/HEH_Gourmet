import {NavLink} from "react-router-dom";
import React from "react";
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
					<Row className="justify-content-between" style={{color: "black"}}>
						<Col xs="auto">
							<Card.Title style={{marginBottom: "0"}}>{product.name.charAt(0).toUpperCase() + product.name.slice(1)}</Card.Title>
							<p style={{textAlign: "center", opacity: "0.8", fontSize: "0.7em", marginBottom: "0"}}>{product.type !== null ? product.type.typeName : ""}</p>
						</Col>
						<Col xs="auto" className="d-flex align-items-center">
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