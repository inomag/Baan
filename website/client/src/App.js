import { Fragment } from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";

import Home from "./components/Home";
import SignupStepForm from './authentication/signup/SignupStepForm';
import Navbar from "./components/Navbar";
import AdminRoute from "./authentication/protectedRoutes/AdminRoute";
import PrivateRoute from "./authentication/protectedRoutes/PrivateRoute";
import { NotFound } from "./404/NotFound";
import SigninStepForm from "./authentication/signin/SigninStepForm";
import Dashboard from "./components/UserDashboard";
import AffectedLocations from "./components/AffectedLocations";

const App = () => {
  return (
    <Fragment>
      <BrowserRouter>
        <Navbar />
        <Switch>
          <Route exact path='/signup' component={SignupStepForm} />
          <Route exact path='/signin' component={SigninStepForm} />
          <PrivateRoute exact path='/' component={Dashboard} />
          <AdminRoute exact path='/manage' component={Home} />
          <AdminRoute exact path='/affected-data' component={AffectedLocations} />
          <Route path="*" component={NotFound} />
        </Switch>
      </BrowserRouter>
    </Fragment>
  );
}

export default App;