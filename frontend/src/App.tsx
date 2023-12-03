import Header from "./components/header/Header.tsx";
import './App.css'
import {Route, Routes} from "react-router-dom";
import AddPage from './pages/AddPage.tsx';
import HomePage from './pages/HomePage.tsx';

function App() {

  return (
    <>
        <Header />
        <Routes>
            <Route path={"/"} element={<HomePage />} />
            <Route path={"insurances/add"} element={<AddPage/>} />
        </Routes>
    </>
  )
}

export default App
