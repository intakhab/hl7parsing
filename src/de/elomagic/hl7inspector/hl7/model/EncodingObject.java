/*
 * Copyright 2006 Carsten Rambow
 * 
 * Licensed under the GNU Public License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.gnu.org/licenses/gpl.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.elomagic.hl7inspector.hl7.model;

/**
 *
 * @author rambow
 */
public class EncodingObject extends RepetitionField {
    
    /** Creates a new instance of Subcomponent */
    public EncodingObject() { }
    
    public void parse(String text) { value = text; }
    
    public String toString() { return value; }
        
    public char getSubDelimiter() { return (char)0; }
    
    public Hl7Object getNewClientInstance() { return null; }
    
    private  String value = "^~\\&";
    
    protected boolean isNULL() { return value.length() == 0; }
}
