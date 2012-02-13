package org.wso2.eventing;

import org.wso2.eventing.exceptions.EventException;

/*
 * Copyright 2005-2009 WSO2, Inc. (http://wso2.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Class that implements the custom implementations of event dispatching. 
 * @param <T>
 */
public interface EventDispatcher <T>{
    public boolean send(Event<T> event, Subscription subscription) throws EventException;
}
