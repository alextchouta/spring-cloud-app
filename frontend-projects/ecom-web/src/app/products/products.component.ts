import {Component, OnInit} from '@angular/core';
import {CatalogueService} from '../catalogue.service';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {AuthentificationService} from '../services/authentification.service';
import {Product} from '../model/product.model';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  private products;
  private editPhoto: boolean;
  private currentProduct: any;
  private selectedFiles: any;
  private progress: number;
  private currentFileUpload: any;

  private timeStamp: number = 0;

  private title: string

  constructor(private catalogueService: CatalogueService,
              private route: ActivatedRoute,
              private router: Router,
              public authService:AuthentificationService) {
  }

  ngOnInit() {
    this.router.events.subscribe((value) => {
      if (value instanceof NavigationEnd) {

        let url = value.url;

        console.log('url' + ' ' + url);

        let p1 = this.route.snapshot.params.p1;

        if (p1 == 1) {
          this.title = "Selection";
          this.getProducts('/products/search/selectedProducts');
        } else if (p1 == 2) {

          let idCat = this.route.snapshot.params.p2;
          this.title = "Produits de la categorie" + " " + idCat;
          this.getProducts('/categories/' + idCat + '/products');
        }else if (p1 == 3) {
          this.title = "Produits en Promotion";
          this.getProducts('/products/search/promoProducts');
        }else if (p1 == 4) {

          this.title = "Produits Disponibles";
          this.getProducts('/products/search/dispoProducts');
        }else if (p1 == 5) {

          this.getProducts('/products/search/dispoProducts');
        }

      }
    });

    let p1 = this.route.snapshot.params.p1;

    if (p1 == 1) {
      this.getProducts('/products/search/selectedProducts');
    }
  }

  onEditPhoto(p: any) {
    this.currentProduct = p;
    this.editPhoto = true;
  }

  onSelectedFile(event) {
    this.selectedFiles = event.target.files;
  }

  private getProducts(url: string) {
    this.catalogueService.getResource(url).subscribe(data => {
      this.products = data;
    }, error1 => {
      console.log(error1);
    });
  }


  uploadPhoto() {
    this.progress = 0;
    this.currentFileUpload =this.selectedFiles.item(0);
    this.catalogueService.uploadPhotoProduct(this.currentFileUpload, this.currentProduct.id).subscribe(event =>{
      if(event.type==HttpEventType.UploadProgress){
        this.progress = Math.round(100*event.loaded/event.total)
      }else if (event instanceof HttpResponse){
       this.timeStamp = Date.now();
      }
    }, error => {
      alert("Probleme de chargement" + "" + JSON.parse(error));
    })
  }

  getTS() {
    return this.timeStamp;
  }

  onAddProductToCaddy(p: Product) {

  }

  onProductDetails(p: Product) {
    let url=btoa(p._links.product.href);
    this.router.navigateByUrl("product-detail/"+url);
  }
}
