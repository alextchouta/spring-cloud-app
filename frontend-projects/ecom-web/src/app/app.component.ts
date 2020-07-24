import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CatalogueService} from './catalogue.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthentificationService} from './services/authentification.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'ecom-web';

  private categories;
  private currentCategorie;

  constructor(private catalogueService: CatalogueService,
              private  router: Router,
              private authService:AuthentificationService) { }

  ngOnInit(): void {
    this.authService.loadAuthenticatedUserFromLocalStorage();
    this.getCategories();
  }

  private getCategories() {

    this.catalogueService.getResource("/categories").subscribe(data =>{
      this.categories = data;
    }, error => {
      console.log(error);
    });
  }

  getProductsByCat(c: any) {
    this.currentCategorie = c;
    this.router.navigateByUrl('/products/2/' + c.id);
  }

  onSelectedProducts() {
    this.currentCategorie = undefined;
    this.router.navigateByUrl("/products/1/0");
  }

  OnProductsPromo() {
    this.currentCategorie = undefined;
    this.router.navigateByUrl("/products/3/0");
  }

  OnProductsDispo() {
    this.currentCategorie = undefined;
    this.router.navigateByUrl("/products/4/0");
  }

  onLogout() {
    this.authService.removeTokenFromLocalStorage();
    this.router.navigateByUrl('/login');
  }
}
