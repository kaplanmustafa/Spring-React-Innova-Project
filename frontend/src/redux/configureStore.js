import { createStore, applyMiddleware, compose } from "redux";
import authReducer from "./authReducer";
import SecureLS from "secure-ls";
import thunk from "redux-thunk";
import { setAuthorizationHeader } from "../api/ApiCalls";

const secureLS = new SecureLS();

const getStateFromStorage = () => {
  const innovaAuth = secureLS.get("innova-auth");

  let stateInLocalStorage = {
    isLoggedIn: false,
    username: undefined,
    fullName: undefined,
    role: undefined,
  };

  if (innovaAuth) {
    return innovaAuth;
  }

  return stateInLocalStorage;
};

const updateStateInStorage = (newState) => {
  secureLS.set("innova-auth", newState);
};

const configureStore = () => {
  const initialState = getStateFromStorage();
  setAuthorizationHeader(initialState);

  const composeEnhancers =
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

  const store = createStore(
    authReducer,
    getStateFromStorage(),
    composeEnhancers(applyMiddleware(thunk))
  );

  store.subscribe(() => {
    updateStateInStorage(store.getState());
    setAuthorizationHeader(store.getState());
  });

  return store;
};

export default configureStore;
