import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';



@Injectable()
export class Config {
  private _env: Object;
  private _config: Object;

  constructor(private http: Http) { }

  load() {
    return new Promise((resolve, reject) => {
      this.http.get('app/assets/env.json')
        .map(res => res.json())
        .subscribe((env_data) => {
          this._env = env_data;
          console.log(env_data);
          this.http.get('./' + env_data.env + '.json')
            .map(res => res.json())
            .catch((error: any) => {
              console.error(error);
              return Observable.throw(error.json().error || 'Server error');
            })
            .subscribe((data) => {
              this._config = data;
              resolve(true);
            });
        });
    });
  }

  getEnv(key: any) {
    return this._env[key];
  }

  get(key: any) {
    return this._config[key];
  }
};
