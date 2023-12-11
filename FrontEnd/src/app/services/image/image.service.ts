import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalConstants } from 'src/app/global-constants';

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private API_IMAGES = GlobalConstants.apiURL + '/images';

  constructor(private http: HttpClient) { }

  async uploadImage(image: File, folder: string): Promise<string> {
    const formData: FormData = new FormData();

    formData.append('image', image);
    formData.append('folderName', folder);

    const response = await this.http.post(`${this.API_IMAGES}/`, formData, { responseType: 'text' }).toPromise();
    return response as string;
  }

  async updateImage(image: File, path: string) {
    const formData: FormData = new FormData();

    formData.append('image', image);
    formData.append('path', path);

    await this.http.put(`${this.API_IMAGES}/`, formData).toPromise();
  }
}
