import links from "./settings";

const URL = links.server;

function apiFacade() {
 
  const registerUser = (user) => {
    const options = makeOptions("POST", false, {
      ...user,
    });

    
    return fetch(URL + "/api/user", options).then(handleHttpErrors);
  };
  const registerDog = (dog) => {
    const options = makeOptions("POST", false, {
      ...dog,
    });

    
    return fetch(URL + "/api/dog/add", options).then(handleHttpErrors);
  };
 
  const setToken = (token) => {
    localStorage.setItem("jwtToken", token);
  };
  const setActiveUser = (user) => {
    localStorage.setItem("user", user);
  };
  const getToken = () => {
    return localStorage.getItem("jwtToken");
  };
  const getActiveUser = () => {
    return localStorage.getItem("user");
  };
  const loggedIn = () => {
    console.log("chcecking if logged in in facade");
    const loggedIn = getToken() != null;
    return loggedIn;
  };
  const logout = () => {
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("user");
  };

  const login = (user, password) => {
    const options = makeOptions("POST", true, {
      username: user,
      password: password,
    });
    return fetch(URL + "/api/login", options)
      .then(handleHttpErrors)
      .then((res) => {
        setToken(res.token);
        setActiveUser(user);
      });
  };

  const fetchData = (url2) => {
    const options = makeOptions("GET", true); //True add's the token
    return fetch(URL + url2, options).then(handleHttpErrors);
  };

  const fetchAnyGET = (URL) => {
    const options = makeOptions("GET", false);
    return fetch(URL, options).then(handleHttpErrors);
  };
  const fetchNoOptions = (URL) => {
    return fetch(URL).then(handleHttpErrors);
  };

  const makeOptions = (method, addToken, body) => {
    var opts = {
      method: method,
      headers: {
        "Content-type": "application/json",
        Accept: "application/json",
      },
    };
    if (addToken && loggedIn()) {
      opts.headers["x-access-token"] = getToken();
    }
    if (body) {
      opts.body = JSON.stringify(body);
    }
    return opts;
  };
  return {
    makeOptions,
    setToken,
    getToken,
    loggedIn,
    login,
    logout,
    fetchData,
    registerUser,
    fetchAnyGET,
    fetchNoOptions,
    getActiveUser,
    registerDog,
  };
}
const facade = apiFacade();

function handleHttpErrors(res) {
  if (!res.ok) {
    return Promise.reject({ status: res.status, fullError: res.json() });
  }

  return res.json();
}
export default facade;
