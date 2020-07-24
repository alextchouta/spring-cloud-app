import { Injectable } from '@angular/core';
import {HttpClient, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from './model/product.model';



@Injectable({
  providedIn: 'root'
})
export class CatalogueService {

  public host:string = "http://localhost:8080"

  public getResource(url:string){
    return this.http.get(this.host+url)
  }

  constructor(private http: HttpClient) { }


  uploadPhotoProduct(file: File, idProduct) {

    let formdata: FormData = new FormData();

    formdata.append('file', file);

    const req = new HttpRequest('POST', this.host + '/uploadPhoto/'+ idProduct, formdata,{
      reportProgress: true,
      responseType: 'text'
    })
    return this.http.request(req);
  }

  public getProduct(url:string):Observable<Product>{

    return this.http.get<Product>(url);
  }
}