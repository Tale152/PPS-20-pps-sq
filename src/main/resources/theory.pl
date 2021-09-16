/*
 * S: The starting story_node id.
 * E: The ending story_node id.
 * L: A list containing all the story_nodes id crossed from S to E.
 * path(+S, +E, -L)
 */
% Match the starting story_node using S.
path(S, E, L) :- story_node(S, _, P), path(S, E, P, L).

% Base case. E is found in the first pathway.
path(S, E, [pathway(E, _)|_], [S, E]).
% E is not in the first pathway, iterate through the other pathways of the same node.
path(S, E, [pathway(_, _)|RP], L) :- path(S, E, RP, L).
% E is not in the first pathway. Search E in the pathways of ID.
path(S, E, [pathway(ID, _)|_], [S|L]) :- path(ID, E, L).

/*
 * Create a List L containing all the story_nodes id crossed starting from the story_node with id S.
 * S: The starting story_node id.
 * L: A list containing all the story nodes crossed from S to a final node.
 * reach_all_final_nodes(+S, -L)
 */
reach_all_final_nodes(S,L) :- story_node(E,_,[]), path(S,E,L).

/*
 * Find all the solutions of reach_all_final_nodes(+S, -L).
 * S: The starting story_node id.
 * R: A list of lists of ids of the story_node crossed to reach all the final nodes starting from S.
 * all_final_nodes_solutions(+S, -R)
 */
all_final_nodes_solutions(S,R) :- findall(L, reach_all_final_nodes(S,L), R).

/*
 * S: The starting story_node id.
 * E: The ending story_node id.
 * D: The description of the narrative of the pathway from the stating story_node id to the ending story_node id.
 * pathway_description(?S, ?E, ?D)
 */
pathway_description(S,E,D) :- story_node(S,_,P),
                              pathway_description(E,P,D).
% Base case. E is found in the first pathway.
pathway_description(E,[pathway(E,D)|_],D).
% E is not in the first pathway, iterate through the other pathways of the same node.
pathway_description(E,[pathway(_,_)|RP],D) :- pathway_description(E,RP,D).

/*
 * ID: The story_node id.
 * N: The description of the story_node with the correct id.
 * story_node_narrative(?ID, ?N)
 */
story_node_narrative(ID, N) :- story_node(ID, N, _).

/*
 * Map the List of story_nodes id in input to a list of story_nodes narrative and pathway description.
 * IDs: List of crossed story_node's ids.
 * W: Narratives and descriptions of the story_node and pathway crossed.
 * walkthrough(+IDs, -W])
 */
% Base case.
walkthrough([],[]).
% More than one ID found. Add the narrative of the first story_node and the pathway description to the result.
walkthrough([ID1, ID2|T], [N, D|W]) :- story_node_narrative(ID1, N),
                                       pathway_description(ID1, ID2, D),
                                       walkthrough([ID2|T], W).
% Final case. Only 1 ID remainig, just adding the narrative.
walkthrough([ID], N) :- story_node_narrative(ID, N).

/*
 * Get the walkthrough of the story starting from story_node S.
 * S: The starting story_node id.
 * W: Narratives and descriptions of the story_node and pathway crossed.
 * story_walkthrough(+S, -W)
 */
story_walkthrough(S, W) :- reach_all_final_nodes(S, L), walkthrough(L, W).

/*
 * Find all the solutions of story_walkthrough(+S, -W).
 * S: The starting story_node id.
 * R: A list of narratives and descriptions of the story_node and pathway crossed in all the stories possible.
 * all_story_walkthrough(+S,-R)
 */
all_story_walkthrough(S, R) :- findall(W, story_walkthrough(S, W), R).