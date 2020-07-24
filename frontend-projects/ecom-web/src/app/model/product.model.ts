export interface Product {
  id:number;
  name:string;
  description:string;
  currentprice:number;
  promotion:boolean;
  selected:boolean;
  available:boolean;
  photoName:string;
  quantity:number;
  _links:{
    self:{
      href:string;
    },
    product:{
      href:string;
    },
    category:{
      href:string;
    }
  }

}
