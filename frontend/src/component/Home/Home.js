import {Stack, Container} from 'react-bootstrap';
import {useAppContext} from "../../store/AppContext";
import HomeCarousel from "./HomeCarousel";
import HomeInfo from "./HomeInfo";

function Home() {
    const { state } = useAppContext();

    return (
        <Container style={{background: "#FFF", minHeight: "100vh"}}>
            <Stack style={{width: "100%"}}>
                <HomeInfo />
                {state.user !== "" ? <HomeCarousel /> : <></>}
            </Stack>
        </Container>
    );
}

export default Home;