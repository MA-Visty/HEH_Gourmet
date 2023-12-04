import {Stack, Carousel, Row, Col, Button} from 'react-bootstrap';
import {useEffect, useState} from "react";
import { Link } from "react-router-dom";
import axios from "axios";

function Home() {
    const [data, setData] = useState([]);
    const [dataFilter, setDataFilter] = useState([]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios
            .get("http://localhost:3000/api/product")
            .then((response) => {
                setData(response.data.products);
                setDataFilter(response.data.products);
                setLoading(false);
            })
            .catch((error) => {
                setCrash(true);
            });
    }, []);

    return (
        <Stack>
            <img style={{objectFit: 'cover', display:"flex", marginLeft:"auto", marginRight:"auto", height:350}} src="/bulb.svg" alt="error" />

            <Carousel style={{background:"#242526"}}>
                {data.map((item) => (
                    <Carousel.Item>
                        <img style={{objectFit:'fit', display:"flex", marginLeft:"auto", marginRight:"auto", height:350, width:"auto"}} src={item.mainImage} alt="error" />
                        <Carousel.Caption style={{background:"#24252655"}}>
                            <h3>{item.name}</h3>
                            <p></p>
                            <Row className="d-flex justify-content-center">
                                <Col xs="auto">
                                    <Button variant="secondary" as={Link} to={`/product/${item.id}`}>
                                        <img style={{objectFit:'cover', display:"flex", marginLeft:"auto", marginRight:"auto", height:"1.75rem", width:"auto"}} src="/menu.svg" alt="error" />
                                    </Button>
                                </Col>
                                <Col xs="auto">
                                    <Button variant="primary" style={{height:"2.5rem", width:"auto"}} className="w-100">
                                        Add ( {item.price} € )
                                    </Button>
                                </Col>
                            </Row>
                        </Carousel.Caption>
                    </Carousel.Item>
                ))}
            </Carousel>
        </Stack>
    );
}

export default Home;