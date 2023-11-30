import Carousel from 'react-bootstrap/Carousel';

function Home() {
    const images = [
        "/Icon.svg",
        "/trash.svg",
        "/bulbComplet.svg",
        "/cart.svg"]

    return (
        <Carousel>
            {images.map((img) => (
                <Carousel.Item>
                    <img src={img} alt="error" width={512} />
                    <Carousel.Caption>
                        <h3>First slide label</h3>
                        <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
                    </Carousel.Caption>
                </Carousel.Item>
            ))}
        </Carousel>
    );
}

export default Home;