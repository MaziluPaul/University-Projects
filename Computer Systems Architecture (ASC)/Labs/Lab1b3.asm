;3. z=(3+(c*c))/(6-(b*b))+((a*a-b*b)/(a*a+c*c))

ASSUME CS:code, DS:data

data SEGMENT

a db 1
b db 2
c db 1
rez dw ?

data ENDS

code SEGMENT

start:
mov AX, data
mov DS, AX

mov AL, b
mul b
;AX = b*b

mov BX, AX
;CX = b*b

mov AL, 6
sub AX, BX
;AX = 6 - (b*b)
mov CX, AX
;CX = 6 - (b*b)

mov AL, c
mul c
;AX = c*c
add AX, 3
cwd
idiv CX
mov CX, AX
;CX = (3+(c*c))/(6-(b*b))


mov AL, a
mul a
mov DX, AX
;DX = a*a

mov AL, c
mul c

add AX,DX
;AX = a*a+c*c
mov BX,AX
;BX = (a*a+c*c)

mov AL, b
mul b

mov DX, AX
;DX = b*b

mov AL, a
mul a
;AX = a*a
sub AX,DX
;AX = (a*a-b*b)
cwd
idiv BX


mov rez, AX
mov AX, 4C00h
int 21h

code ENDS
END start