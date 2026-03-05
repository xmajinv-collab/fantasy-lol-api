import { BrowserRouter,Routes,Route } from "react-router-dom";

import Dashboard from "./pages/Dashboard";
import Mercado from "./pages/Mercado";

function App(){

    return(

        <BrowserRouter>

            <Routes>

                <Route path="/" element={<Dashboard/>}/>
                <Route path="/mercado" element={<Mercado/>}/>

            </Routes>

        </BrowserRouter>

    );
}

export default App;