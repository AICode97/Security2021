import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import './App.css';

import Register from './pages/Register';
import User from './pages/User';
import Login from './pages/Login';
import Admin from "./pages/Admin";
import Header from "./components/Header";
import { useState } from "react";
import UserService from "./services/UserService";
import Logout from "./pages/Logout";

const userService = new UserService()

function App() {
  const [user, setUser] = useState(null)

  const updateUser = async(user) => {
    const response = await userService.user()
    setUser(response.data)
  }

  const removeUser = () => {
    setUser(null)
  }

  return (
      <Router>
        <div> 
          <Header user={ user }/>
        <Switch>
          <Route path="/register">
            <Register updateUser={ updateUser }/>
          </Route>
          <Route path="/user">
            <User updateUser={ updateUser }/>
          </Route>
          <Route path="/admin">
            <Admin  updateUser={ updateUser }/>
          </Route>
          <Route path="/logout">
            <Logout removeUser={ removeUser }/>
          </Route>
          <Route path="/">
            <Login updateUser={ updateUser } />
          </Route>
        </Switch>
        </div>
      </Router>
     

  );
}

export default App;
