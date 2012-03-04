/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.charon.samples.utils;

import org.apache.wink.client.ClientRequest;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.handlers.ClientHandler;
import org.apache.wink.client.handlers.HandlerContext;
import org.wso2.charon.core.client.SCIMClient;
import org.wso2.charon.core.exceptions.AbstractCharonException;
import org.wso2.charon.core.exceptions.CharonException;
import org.wso2.charon.core.protocol.ResponseCodeConstants;
import org.wso2.charon.core.schema.SCIMConstants;


public class CharonResponseHandler implements ClientHandler {

    SCIMClient scimClient = null;

    public void setSCIMClient(SCIMClient scimClient) {
        this.scimClient = scimClient;
    }

    //@Override
    public ClientResponse handle(ClientRequest clientRequest, HandlerContext handlerContext)
            throws Exception {

        //obtain client response
        ClientResponse cr = handlerContext.doChain(clientRequest);
        if (scimClient != null) {
            //see whether the response indicates a failure or success according to the 
            if (!(scimClient.evaluateResponseStatus(cr.getStatusCode()))) {
                AbstractCharonException charonException = null;
                try {
                    String format = SCIMConstants.identifyFormat(cr.getHeaders().getFirst(SCIMConstants.CONTENT_TYPE_HEADER));
                    charonException = scimClient.decodeSCIMException(cr.getEntity(String.class), format);
                } catch (CharonException e) {
                    System.out.println(e.getDescription());
                }
                //log and throw the actual exception return in the response.
                System.out.println(charonException.getDescription());
                throw charonException;
            } else {
                return cr;
            }
        }
        return cr;
    }
}