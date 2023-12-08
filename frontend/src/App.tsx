import Header from "./components/header/Header.tsx";
import './App.css'
import {Route, Routes} from "react-router-dom";
import AddPage from './pages/AddPage.tsx';
import HomePage from './pages/HomePage.tsx';
import DetailsPage from "./pages/DetailsPage.tsx";

function App() {

  return (
    <>
        <Header />
        <Routes>
            <Route path={"/"} element={<HomePage />} />
            <Route path={"insurances/add"} element={<AddPage/>} />
            <Route path={"/details/:type/:id"} element={<DetailsPage/>} />
        </Routes>
    </>
  )
}

export default App
