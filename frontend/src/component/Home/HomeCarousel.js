import {Carousel, Row} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import axios from "axios";
import HomeCarouselForm from "./HomeCarouselForm";
import {useAppContext} from "../../store/AppContext";
import API_URL from "../../apiConfig";

function HomeCarousel() {
    const { state } = useAppContext();
    const [data, setData] = useState([]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios
            .get(`${API_URL}/api/products`)
            .then((response) => {
                response.data.map((product) => {
                    if(state.favorite.includes(product.ID)) {
                        data.push(product);
                    }
                })
                setLoading(false);
            })
            .catch((error) => {
                setCrash(true);
            });
    }, []);

    return (
        <Carousel style={{background: "#242526"}}>
            {data.map((product) => (
                <Carousel.Item>
                    <img style={{
                        objectFit: 'fit',
                        display: "flex",
                        marginLeft: "auto",
                        marginRight: "auto",
                        height: 350,
                        width: "auto"
                    }} src={product.image} alt="error"/>
                    <Carousel.Caption style={{background: "#24252655"}}>
                        <h3>{product.name}</h3>
                        <p></p>
                        <Row className="d-flex justify-content-center">
                            <HomeCarouselForm product={product}/>
                        </Row>
                    </Carousel.Caption>
                </Carousel.Item>
            ))}
        </Carousel>
    );
}

export default HomeCarousel;