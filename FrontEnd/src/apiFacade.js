import links from "./settings";

const URL = links.server;

function apiFacade() {
 
  const registerUser = (user) => {
    const options = makeOptions("POST", false, {
      ...user,
    });
    return fetch(URL + "/api/user", options).then(handleHttpErrors);
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
  function setWithExpiry(key, value, ttl) {
    const now = new Date();

    // `item` is an object which contains the original value
    // as well as the time when it's supposed to expire
    const item = {
      value: value,
      expiry: now.getTime() + ttl,
    };
    localStorage.setItem(key, JSON.stringify(item));
  }

  //https://www.sohamkamani.com/blog/javascript-localstorage-with-ttl-expiry/
  function getWithExpiry(key) {
    const itemStr = localStorage.getItem(key);
    // if the item doesn't exist, return null
    if (!itemStr) {
      return null;
    }
    const item = JSON.parse(itemStr);
    const now = new Date();
    // compare the expiry time of the item with the current time
    if (now.getTime() > item.expiry) {
      // If the item is expired, delete the item from storage
      // and return null
      localStorage.removeItem(key);
      return null;
    }
    return item.value;
  }

  function getActivUser() {
    return getWithExpiry("user");
  }
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
    getActivUser,
    setWithExpiry
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
