import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from 'react-redux';
import { store, persistor } from './redux/store';
import { PersistGate } from 'redux-persist/integration/react';
import { getTotals } from './redux/cartRedux';
const root = ReactDOM.createRoot(document.getElementById('root'));

store.dispatch(getTotals());

root.render(
  <Provider store={store} >
    <PersistGate loading={null} persistor={persistor}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </PersistGate>
  </Provider >
);
