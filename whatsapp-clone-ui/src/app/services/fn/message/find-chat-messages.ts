/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { MessageResponse } from '../../models/message-response';

export interface FindChatMessages$Params {
  'chat-id': string;
}

export function findChatMessages(http: HttpClient, rootUrl: string, params: FindChatMessages$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<MessageResponse>>> {
  const rb = new RequestBuilder(rootUrl, findChatMessages.PATH, 'get');
  if (params) {
    rb.path('chat-id', params['chat-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<MessageResponse>>;
    })
  );
}

findChatMessages.PATH = '/api/v1/messages/chat/{chat-id}';
