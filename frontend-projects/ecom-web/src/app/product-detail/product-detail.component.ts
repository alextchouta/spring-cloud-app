import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CatalogueService} from '../catalogue.service';
import {Product} from '../model/product.model';
import {AuthentificationService} from '../services/authentification.service';
import {HttpEventType, HttpResponse} from '@angular/common/http';


@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  private currentProduct: Product;
  mode: number = 0;
  private editPhoto: boolean;
  private selectedFiles: any;
  private progress: number;
  private currentFileUpload: any;
  private currentTime: number;

  constructor(private router:Router,
              private route:ActivatedRoute,
              private catalService:CatalogueService,
              private authService:AuthentificationService) { }

  ngOnInit() {
  this.getProduct();
  }

  public getProduct(){
    let url = atob(this.route.snapshot.params.url);
    this.catalService.getProduct(url).subscribe(product =>{
      this.currentProduct=product;
    })
    console.log(url);
  }

  onEditPhoto(p: any) {
    this.currentProduct = p;
    this.editPhoto = true;
  }

  onSelectedFile(event) {
    this.selectedFiles = event.target.files;
  }

  uploadPhoto() {
    this.progress = 0;
    this.currentFileUpload =this.selectedFiles.item(0);
    this.catalService.uploadPhotoProduct(this.currentFileUpload, this.currentProduct.id).subscribe(event =>{
      if(event.type==HttpEventType.UploadProgress){
        this.progress = Math.round(100*event.loaded/event.total)
      }else if (event instanceof HttpResponse){
        this.currentTime = Date.now();
      }
    }, error => {
      alert("Probleme de chargement" + "" + JSON.parse(error));
    })
  }

  getTS() {
    return this.currentTime;
  }

  onEditProduct() {
    this.mode==1;
  }
  onUpdateProduct(data){

  }

  onAddProductToCaddy(p: any) {

  }
}
