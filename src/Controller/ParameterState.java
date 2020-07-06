/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Bryan Hernandez
 */
public enum ParameterState {
    Sync_Send_Blocking,
    Sync_Send_NonBlocking,
    Sync_Receive_Blocking,
    Sync_Receive_NonBlocking,
    Sync_Receive_ProofOfArrival,
    
    Addr_Direct_Send,
    Addr_Direct_Receive_Explicit,
    Addr_Direct_Receive_Implicit,
    Addr_Indirect_Static,
    Addr_Indirect_Dynamic,
    
    Form_Content,
    Form_Lenght_Fixed,
    Form_Lenght_Variable,
    
    Queue_FIFO,
    Queue_Priority
    
}
