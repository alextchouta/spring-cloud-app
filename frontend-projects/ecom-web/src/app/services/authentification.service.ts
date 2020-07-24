import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {
  private users = [
    {username: 'admin', password: '1234', roles: ['ADMIN', 'USER']},
    {username: 'user1', password: '1234', roles: ['USER']},
    {username: 'user2', password: '1234', roles: ['USER']}
  ];

  public isAuthenticated: boolean;
  public userAuthenticated;
  public token:string;

  constructor() {
  }

// verifir les credentials
  public login(username: string, password: string) {
    let user;
    this.users.forEach(u => {
      if (u.username == username && u.password == password) {
        user = u;
        //codage du token avec la methode btoa
        this.token = btoa(JSON.stringify({username: u.username, roles: u.roles}));
      }
    });
    if (user) {
      this.isAuthenticated = true;
      this.userAuthenticated = user;
    } else {
      this.isAuthenticated = false;
      this.userAuthenticated = undefined;
    }
  }

  // Verifie si il est Admin
  public isAdmin() {
    if (this.isAuthenticated) {

      if (this.userAuthenticated.roles.indexOf('ADMIN') > -1) {
        return true;
      }
      return false;
    }
  }

  // sauvegarder les donnees de celui qui s est authentifie
  public saveAuthenticatedUser() {
    if (this.isAuthenticated) {
      localStorage.setItem('autToken', this.token);
    }
  }

  // charge les donnees sauvergardees qui sont dans le local storage
  public loadAuthenticatedUserFromLocalStorage() {

    let t = localStorage.getItem('authToken');
    if (t) {
      let user = JSON.parse(atob(t));
      this.userAuthenticated = {username: user.username, roles: user.roles};
      this.isAuthenticated = true;
      this.token = t;
    }
  }

  public removeTokenFromLocalStorage(){
    localStorage.removeItem('autToken');
    this.isAuthenticated = false;
    this.token = undefined;
    this.userAuthenticated= undefined;
  }
}
