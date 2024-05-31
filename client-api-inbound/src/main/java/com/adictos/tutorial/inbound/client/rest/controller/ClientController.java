package com.adictos.tutorial.inbound.client.rest.controller;

import com.adictos.tutorial.domain.client.port.ClientInboundPort;
import com.adictos.tutorial.inbound.client.rest.dto.ChangeNameReq;
import com.adictos.tutorial.inbound.client.rest.dto.ClientCreateReq;
import com.adictos.tutorial.inbound.client.rest.dto.ClientRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {
	private final ClientInboundPort port;

	public ClientController(ClientInboundPort port) {
		this.port = port;
	}

	@PostMapping()
	public ResponseEntity<ClientRes> create(@RequestBody ClientCreateReq clientToCreate) {
		var client = port.create(ClientCreateReq.toDomain(clientToCreate));
		return ResponseEntity.status(HttpStatus.CREATED).body(ClientRes.toResponse(client));
	}

	@GetMapping()
	public List<ClientRes> getAll() {
		return ClientRes.toResponse(port.findAll());
	}

	@PutMapping("/{clientId}/name")
	public ClientRes changeClientNameById(
			@PathVariable Long clientId,
			@RequestBody ChangeNameReq changeNameReq
	) {
		return ClientRes.toResponse(port.updateClientNameById(clientId, changeNameReq.newName()));
	}
}
