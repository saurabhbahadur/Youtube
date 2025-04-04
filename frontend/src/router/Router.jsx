import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from '../components/Navbar'
import Home from '../pages/Home'
import Channel from '../pages/Channel'
import Footer from '../components/Footer'


const Router = () => {
  return (
    <BrowserRouter>
    <Navbar/>
    <Routes>
        <Route path='/' element={<Home/>} />
        <Route path='/channels' element={<Channel/>} />
    </Routes>
    <Footer/>
    </BrowserRouter>
  )
}

export default Router